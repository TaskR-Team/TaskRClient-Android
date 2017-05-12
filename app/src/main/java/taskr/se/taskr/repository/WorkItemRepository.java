package taskr.se.taskr.repository;

import java.util.List;

import taskr.se.taskr.model.WorkItem;

/**
 * Created by kawi01 on 2017-05-11.
 */

public interface WorkItemRepository {
     List<WorkItem> getWorkItems();
     List<WorkItem> getUnstartedWorkItems();
     List<WorkItem> getStartedWorkItems();
     List<WorkItem> getDoneWorkItems();
     List<WorkItem> getMyWorkItems();
     WorkItem getWorkItem(long id);
     long addOrUpdateWorkItem(WorkItem workItem);
     void deleteWorkItem(WorkItem workItem);
}