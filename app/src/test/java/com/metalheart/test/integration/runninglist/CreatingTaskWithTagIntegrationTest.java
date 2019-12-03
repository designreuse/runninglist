package com.metalheart.test.integration.runninglist;

import com.metalheart.model.TaskModel;
import com.metalheart.model.jpa.Task;
import com.metalheart.model.rest.request.CreateTaskRequest;
import com.metalheart.model.rest.response.RunningListViewModel;
import com.metalheart.model.rest.response.TagViewModel;
import com.metalheart.model.rest.response.TaskViewModel;
import com.metalheart.service.RunningListCommandManager;
import com.metalheart.service.RunningListService;
import com.metalheart.service.TagService;
import com.metalheart.service.TaskService;
import com.metalheart.test.integration.BaseIntegrationTest;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;

public class CreatingTaskWithTagIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RunningListService runningListService;

    @Autowired
    private RunningListCommandManager commandManager;


    @Test
    public void testCreatingWithTags() throws Exception {

        // arrange
        CreateTaskRequest request = generateRandomCreateTaskRequest();
        request.setTags(asList(
            TagViewModel.builder().text("tag1").build(),
            TagViewModel.builder().text("tag2").build()
        ));


        // act
        Task task = taskService.createTask(request);


        // assert
        List<TaskViewModel> tasks = runningListService.getRunningList().getTasks();
        Assert.assertFalse(CollectionUtils.isEmpty(tasks));

        List<TagViewModel> tags = tasks.get(0).getTags();
        Assert.assertEquals(2, tags.size());
    }

    @Test
    public void testUndoCreatingWithTags() throws Exception {

        // arrange
        CreateTaskRequest request = generateRandomCreateTaskRequest();
        request.setTags(asList(
            TagViewModel.builder().text("tag1").build(),
            TagViewModel.builder().text("tag2").build()
        ));


        // act
        Task task = taskService.createTask(request);
        commandManager.undo();

        // assert
        List<TaskViewModel> tasks = runningListService.getRunningList().getTasks();
        Assert.assertTrue(CollectionUtils.isEmpty(tasks));
    }

    @Test
    public void testRedoCreatingWithTags() throws Exception {

        // arrange
        CreateTaskRequest request = generateRandomCreateTaskRequest();
        request.setTags(asList(
            TagViewModel.builder().text("tag1").build(),
            TagViewModel.builder().text("tag2").build()
        ));


        // act
        Task task = taskService.createTask(request);
        commandManager.undo();
        commandManager.redo();

        // assert
        List<TaskViewModel> tasks = runningListService.getRunningList().getTasks();
        Assert.assertFalse(CollectionUtils.isEmpty(tasks));

        List<TagViewModel> tags = tasks.get(0).getTags();
        Assert.assertEquals(2, tags.size());
    }
}
