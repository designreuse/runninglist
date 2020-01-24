package com.metalheart.test.integration.runninglist;

import com.metalheart.model.RunningList;
import com.metalheart.model.Tag;
import com.metalheart.model.Task;
import com.metalheart.service.RunningListCommandService;
import com.metalheart.service.RunningListService;
import com.metalheart.service.TagService;
import com.metalheart.service.TaskService;
import com.metalheart.test.integration.BaseIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class TagIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RunningListService runningListService;

    @Autowired
    private RunningListCommandService runningListCommandService;

    @Test
    public void testCreating() {

        // arrange
        Integer userId = generateUser();
        Task createRequest = getTask(userId, "Created task");
        Task createdTask = runningListCommandService.createTask(userId, createRequest);

        // act
        tagService.addTagToTask("tag1", createdTask.getId());

        // assert
        List<Tag> tags = tagService.getTags(userId);
        Task fetchedTask = taskService.getTask(createdTask.getId());
        Assert.assertNotNull(fetchedTask);
        Assert.assertFalse(CollectionUtils.isEmpty(fetchedTask.getTags()));
        Assert.assertFalse(CollectionUtils.isEmpty(tags));
        Assert.assertTrue(fetchedTask.getTags().containsAll(tags));
    }

    @Test
    public void testSelection() {

        // arrange
        Integer userId = generateUser();
        Task createdTask1 = runningListCommandService.createTask(userId, getTask(userId, "task1"));
        Task createdTask2 = runningListCommandService.createTask(userId, getTask(userId, "task2"));
        Task createdTask3 = runningListCommandService.createTask(userId, getTask(userId, "task3"));
        tagService.addTagToTask("tag1", createdTask1.getId());
        tagService.addTagToTask("tag1", createdTask2.getId());


        // act
        tagService.selectTag(userId, "tag1");

        // assert

        RunningList runningList = runningListService.getRunningList(userId);
        Assert.assertEquals(2, runningList.getTasks().size());
    }

    @Test
    public void testSeveralTagSelection() {

        // arrange
        Integer userId = generateUser();
        Task createdTask1 = runningListCommandService.createTask(userId, getTask(userId, "task1"));
        Task createdTask2 = runningListCommandService.createTask(userId, getTask(userId, "task2"));
        Task createdTask3 = runningListCommandService.createTask(userId, getTask(userId, "task3"));
        tagService.addTagToTask("tag1", createdTask1.getId());
        tagService.addTagToTask("tag2", createdTask1.getId());
        tagService.addTagToTask("tag1", createdTask2.getId());
        tagService.addTagToTask("tag2", createdTask2.getId());
        tagService.addTagToTask("tag3", createdTask3.getId());


        // act
        tagService.selectTag(userId, "tag1");

        // assert
        Assert.assertEquals(1, tagService.getSelectedTags(userId).size());

        RunningList runningList = runningListService.getRunningList(userId);

        List<Task> tasks = runningList.getTasks();
        Assert.assertEquals(2, tasks.size());
        Assert.assertEquals(2, tasks.get(0).getTags().size());
        Assert.assertEquals(2, tasks.get(1).getTags().size());
    }

    @Test
    public void testSeveralTagSelection2() {

        // arrange
        Integer userId = generateUser();
        Task createdTask1 = runningListCommandService.createTask(userId, getTask(userId, "task1"));
        Task createdTask2 = runningListCommandService.createTask(userId, getTask(userId, "task2"));
        Task createdTask3 = runningListCommandService.createTask(userId, getTask(userId, "task3"));
        tagService.addTagToTask("tag1", createdTask1.getId());
        tagService.addTagToTask("tag2", createdTask1.getId());
        tagService.addTagToTask("tag1", createdTask2.getId());
        tagService.addTagToTask("tag2", createdTask2.getId());
        tagService.addTagToTask("tag3", createdTask3.getId());


        // act
        tagService.selectTag(userId, "tag1");
        RunningList runningList = runningListService.getRunningList(userId);
        tagService.selectTag(userId, "tag2");
        runningList = runningListService.getRunningList(userId);

        // assert
        Assert.assertEquals(2, tagService.getSelectedTags(userId).size());



        List<Task> tasks = runningList.getTasks();
        Assert.assertEquals(2, tasks.size());
        Assert.assertEquals(2, tasks.get(0).getTags().size());
        Assert.assertEquals(2, tasks.get(1).getTags().size());
    }

    @Test
    public void testStrictSeveralTagSelection() {

        // arrange
        Integer userId = generateUser();
        Task createdTask1 = runningListCommandService.createTask(userId, getTask(userId, "task1"));
        Task createdTask2 = runningListCommandService.createTask(userId, getTask(userId, "task2"));
        Task createdTask3 = runningListCommandService.createTask(userId, getTask(userId, "task3"));

        tagService.addTagToTask("tag1", createdTask1.getId());
        tagService.addTagToTask("tag2", createdTask1.getId());

        tagService.addTagToTask("tag1", createdTask2.getId());
        tagService.addTagToTask("tag2", createdTask2.getId());

        tagService.addTagToTask("tag1", createdTask3.getId());


        // act
        tagService.selectTag(userId, "tag1");
        tagService.selectTag(userId, "tag2");

        // assert
        Assert.assertEquals(2, tagService.getSelectedTags(userId).size());

        RunningList runningList = runningListService.getRunningList(userId);
        List<Task> tasks = runningList.getTasks();
        Assert.assertEquals(2, tasks.size());
        Assert.assertEquals(2, tasks.get(0).getTags().size());
        Assert.assertEquals(2, tasks.get(1).getTags().size());
    }
}
