package com.metalheart.service;

import com.metalheart.exception.RunningListArchiveAlreadyExistException;
import com.metalheart.model.WeekId;
import com.metalheart.model.jpa.Task;
import com.metalheart.model.rest.request.ChangeTaskStatusRequest;
import com.metalheart.model.rest.request.CreateTaskRequest;
import com.metalheart.model.rest.request.UpdateTaskRequest;

public interface RunningListCommandService {
    Task createTask(CreateTaskRequest request);

    void changeTaskStatus(ChangeTaskStatusRequest request);

    void delete(Integer taskId);

    void update(UpdateTaskRequest request);

    void archive(WeekId weekId) throws RunningListArchiveAlreadyExistException;
}
