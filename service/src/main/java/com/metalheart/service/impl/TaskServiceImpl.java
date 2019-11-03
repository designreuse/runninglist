package com.metalheart.service.impl;

import com.metalheart.model.ChangeTaskPriorityRequest;
import com.metalheart.model.ChangeTaskStatusRequest;
import com.metalheart.model.CreateTaskRequest;
import com.metalheart.model.Task;
import com.metalheart.model.TaskStatus;
import com.metalheart.model.TaskViewModel;
import com.metalheart.model.UpdateTaskRequest;
import com.metalheart.model.WeekWorkLog;
import com.metalheart.model.WeekWorkLogPK;
import com.metalheart.repository.inmemory.ITaskPriorityRepository;
import com.metalheart.repository.jpa.TaskJpaRepository;
import com.metalheart.repository.jpa.WeekWorkLogJpaRepository;
import com.metalheart.service.TaskService;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.metalheart.model.TaskStatus.CANCELED;
import static com.metalheart.model.TaskStatus.DONE;
import static com.metalheart.model.TaskStatus.NONE;

@Slf4j
@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @Autowired
    private WeekWorkLogJpaRepository weekWorkLogJpaRepository;

    @Autowired
    private ITaskPriorityRepository taskPriorityRepository;


    @PostConstruct
    public void reorder() {

        List<Task> taskList = taskJpaRepository.findAllByOrderByPriorityAsc();
        int maxPriority = taskList.size();
        taskPriorityRepository.setMaxPriority(maxPriority);

        saveTaskList(taskList);
    }

    @Override
    public List<TaskViewModel> getTaskList() {

        return taskJpaRepository.findAllByOrderByPriorityAsc().stream()
            .map(task -> TaskViewModel.builder()
                .id(task.getId())
                .status(getDayStatuses(task))
                .title(task.getTitle())
                .description(task.getDescription())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public void createTask(CreateTaskRequest request) {
        Task task = Task.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .priority(taskPriorityRepository.incrementAndGetMaxPriority())
            .createdAt(ZonedDateTime.now())
            .build();
        taskJpaRepository.save(task);
    }

    @Override
    public void delete(Integer taskId) {
        taskJpaRepository.deleteById(taskId);
    }

    @Override
    public void changeTaskStatus(ChangeTaskStatusRequest request) {

        Integer taskId = request.getTaskId();
        Integer dayIndex = request.getDayIndex();
        TaskStatus status = request.getStatus();

        weekWorkLogJpaRepository.save(WeekWorkLog.builder()
            .id(WeekWorkLogPK.builder().taskId(taskId).dayIndex(dayIndex).build())
            .status(status)
            .build());
    }

    @Override
    public void update(UpdateTaskRequest request) {
        Task task = taskJpaRepository.getOne(request.getId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setModifiedAt(ZonedDateTime.now());
        taskJpaRepository.save(task);
    }

    @Override
    public void reorderTask(ChangeTaskPriorityRequest request) {

        List<Task> tasks = taskJpaRepository.findAllByOrderByPriorityAsc();

        Task moved = tasks.get(request.getStartIndex());

        tasks.remove(moved);
        tasks.add(request.getEndIndex(), moved);
        saveTaskList(tasks);
    }


    private List<String> getDayStatuses(Task task) {
        List<WeekWorkLog> taskWorkLog = weekWorkLogJpaRepository.findAllByTaskId(task.getId());

        List<String> res = new ArrayList<>();

        TaskStatus previous = NONE;
        for (int day = 0; day < 7; day++) {
            TaskStatus status = getStatus(taskWorkLog, day);

            if (NONE.equals(status) && previous.equals(NONE)) {

                res.add(NONE.toString());

            } else if (NONE.equals(status) && previous.equals(DONE)) {

                res.add(DONE.toString());

            } else if (DONE.equals(status)) {

                if (!DONE.equals(previous)) {
                    previous = DONE;
                }
                res.add(DONE.toString());

            } else if (NONE.equals(status) && previous.equals(CANCELED)) {

                res.add(CANCELED.toString());

            } else if (CANCELED.equals(status)) {

                if (!CANCELED.equals(previous)) {
                    previous = CANCELED;
                }
                res.add(CANCELED.toString());

            } else {

                res.add(status.toString());
            }

        }
        return res;
    }

    private void saveTaskList(List<Task> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).setPriority(i);
        }
        taskJpaRepository.saveAll(taskList);
    }

    private TaskStatus getStatus(List<WeekWorkLog> taskWorkLog, int day) {
        for (WeekWorkLog log : taskWorkLog) {
            if (log.getId().getDayIndex() == day) {
                return log.getStatus();
            }
        }
        return NONE;
    }
}
