package com.metalheart.controller;

import com.metalheart.EndPoint;
import com.metalheart.model.Task;
import com.metalheart.model.User;
import com.metalheart.model.request.AddTagToTaskRequest;
import com.metalheart.model.request.ChangeTaskPriorityRequest;
import com.metalheart.model.request.ChangeTaskStatusRequest;
import com.metalheart.model.request.CreateTaskRequest;
import com.metalheart.model.request.RemoveTagFromTaskRequest;
import com.metalheart.model.request.UpdateTaskRequest;
import com.metalheart.model.response.Response;
import com.metalheart.model.response.RunningListViewModel;
import com.metalheart.model.response.TagViewModel;
import com.metalheart.service.RunningListCommandService;
import com.metalheart.service.TagService;
import com.metalheart.service.WebService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.metalheart.HTTPConstants.HEADER_TIMEZONE_OFFSET;
import static com.metalheart.config.ServiceConfiguration.APP_CONVERSION_SERVICE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class TaskController {

    @Autowired
    private RunningListCommandService runningListCommandService;

    @Autowired
    private TagService tagService;

    @Autowired
    @Qualifier(APP_CONVERSION_SERVICE)
    private ConversionService conversionService;

    @Autowired
    private WebService webService;

    @PostMapping(path = EndPoint.CREATE_TASK, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createTask(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                               @AuthenticationPrincipal User user,
                                               @Valid @RequestBody CreateTaskRequest request) {

        Task task = conversionService.convert(request, Task.class);
        task.setUserId(user.getId());
        runningListCommandService.createTask(user.getId(), task);

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @PostMapping(path = EndPoint.CHANGE_TASK_STATUS, consumes = APPLICATION_JSON_VALUE, produces =
        APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> changeStatus(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                                 @AuthenticationPrincipal User user,
                                                 @Valid @RequestBody ChangeTaskStatusRequest request) {

        runningListCommandService.changeTaskStatus(user.getId(), request.getTaskId(), request.getDayIndex(),
            request.getStatus());

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @PutMapping(
        path = EndPoint.CHANGE_TASK_PRIORITY,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> reorderTask(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                                @AuthenticationPrincipal User user,
                                                @Valid @RequestBody ChangeTaskPriorityRequest request) {

        runningListCommandService.reorderTask(user.getId(), request.getStartIndex(), request.getEndIndex());

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }


    @DeleteMapping(path = EndPoint.DELETE_TASK, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> delete(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                           @AuthenticationPrincipal User user,
                                           @PathVariable("taskId") Integer taskId) {

        runningListCommandService.delete(user.getId(), taskId);

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @PutMapping(
        path = EndPoint.UPDATE_TASK,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateTask(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                               @AuthenticationPrincipal User user,
                                               @Valid @RequestBody UpdateTaskRequest request) {


        runningListCommandService.update(user.getId(), conversionService.convert(request, Task.class));

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @PostMapping(
        path = EndPoint.ADD_TAG_TO_TASK,
        produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add tag to task", response = RunningListViewModel.class)
    public ResponseEntity<Response> addTaskTag(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                               @AuthenticationPrincipal User user,
                                               AddTagToTaskRequest request) {

        tagService.addTagToTask(request.getTag(), request.getTaskId());

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @DeleteMapping(
        path = EndPoint.REMOVE_TAG_FROM_TASK,
        produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove tag from task", response = RunningListViewModel.class)
    public ResponseEntity<Response> removeTaskTag(@RequestHeader(HEADER_TIMEZONE_OFFSET) Integer timezoneOffset,
                                                  @AuthenticationPrincipal User user,
                                                  RemoveTagFromTaskRequest request) {

        tagService.removeTagFromTask(request.getTag(), request.getTaskId());

        return webService.getResponseBuilder()
            .runningList(user.getId(), timezoneOffset)
            .build();
    }

    @GetMapping(
        path = EndPoint.GET_TASK_TAG_LIST,
        produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove tag from task", response = TagViewModel.class, responseContainer = "List")
    public List<TagViewModel> getTags(@AuthenticationPrincipal User user) {

        return tagService.getTags(user.getId()).stream()
            .map(tag -> conversionService.convert(tag, TagViewModel.class))
            .collect(Collectors.toList());
    }
}
