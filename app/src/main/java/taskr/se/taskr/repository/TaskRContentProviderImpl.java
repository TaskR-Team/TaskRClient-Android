package taskr.se.taskr.repository;

import android.content.Context;

import java.util.List;

import taskr.se.taskr.model.Team;
import taskr.se.taskr.model.User;
import taskr.se.taskr.model.WorkItem;

/**
 * Created by kawi01 on 2017-05-15.
 */

public class TaskRContentProviderImpl implements TaskRContentProvider {

    private final UserRepository userRepository;
    private final WorkItemHttpClient workItemClient;
    private final WorkItemRepository workItemRepository;
    private final RefreshItemsListener refreshItemsListener;
    private static TaskRContentProviderImpl instance;

    public static synchronized TaskRContentProviderImpl getInstance(Context context) {
        if(instance == null) {
            instance = new TaskRContentProviderImpl(context, new RefreshItemsListener() {
                @Override
                public void refreshItems() {

                }
            });
        }
        return instance;
    }

    public static synchronized TaskRContentProviderImpl getInstance(Context context, RefreshItemsListener refreshItemsListener) {
        if(instance == null) {
            instance = new TaskRContentProviderImpl(context, refreshItemsListener);
        }
        return instance;
    }

    private TaskRContentProviderImpl(Context context, RefreshItemsListener refreshItemsListener) {
        userRepository = UserRepositorySql.getInstance(context);
        workItemRepository = WorkItemRepositorySql.getInstance(context);
        workItemClient = WorkItemHttpClient.getInstance(context);
        this.refreshItemsListener = refreshItemsListener;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public long addOrUpdateUser(User user) {
        return 0;
    }

    @Override
    public void removeUser(User user) {

    }

    @Override
    public List<WorkItem> getWorkItems() {
        workItemClient.getWorkItems(new OnResultEventListener<List<WorkItem>>() {
            @Override
            public void onResult(List<WorkItem> result) {
                if (result != null) {
                    syncWorkItems(result);
                }
                refreshItemsListener.refreshItems();
            }
        });
        return workItemRepository.getWorkItems();
    }

    @Override
    public List<WorkItem> getUnstartedWorkItems() {
        workItemClient.getWorkItems(new OnResultEventListener<List<WorkItem>>() {
            @Override
            public void onResult(List<WorkItem> result) {
                if (result != null) {
                    syncWorkItems(result);
                }
                refreshItemsListener.refreshItems();
            }
        });
        return workItemRepository.getUnstartedWorkItems();
    }

    @Override
    public List<WorkItem> getStartedWorkItems() {
        workItemClient.getWorkItems(new OnResultEventListener<List<WorkItem>>() {
            @Override
            public void onResult(List<WorkItem> result) {
                if (result != null) {
                    syncWorkItems(result);
                }
                refreshItemsListener.refreshItems();
            }
        });
        return workItemRepository.getStartedWorkItems();
    }

    @Override
    public List<WorkItem> getDoneWorkItems() {
        workItemClient.getWorkItems(new OnResultEventListener<List<WorkItem>>() {
            @Override
            public void onResult(List<WorkItem> result) {
                if (result != null) {
                    syncWorkItems(result);
                }
                refreshItemsListener.refreshItems();
            }
        });
        return workItemRepository.getDoneWorkItems();
    }

    @Override
    public List<WorkItem> getMyWorkItems() {
        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsByUser(User user) {
        return null;
    }

    @Override
    public List<WorkItem> searchWorkItem(String query) {
        return workItemRepository.searchWorkItem(query);
    }

    @Override
    public WorkItem getWorkItem(long id) {
        return workItemRepository.getWorkItem(id);
    }

    @Override
    public long addOrUpdateWorkItem(final WorkItem workItem) {
        final long id = workItemRepository.addOrUpdateWorkItem(workItem);
        if (workItem.hasBeenSavedToServer()) {
            workItemClient.putWorkItem(workItem);
        } else {
            workItemClient.postWorkItem(workItem, new OnResultEventListener() {
                @Override
                public void onResult(Object generatedKey) {
                    WorkItem _workItem = new WorkItem(id, (String) generatedKey, workItem.getTitle(), workItem.getDescription(), workItem.getStatus());
                    workItemRepository.addOrUpdateWorkItem(workItem);
                }
            });
        }
        return id;
    }

    @Override
    public void removeWorkItem(WorkItem workItem) {
        workItemRepository.removeWorkItem(workItem);
        workItemClient.deleteWorkItem(workItem);
    }

    @Override
    public void assignWorkItem(WorkItem workItem, User user) {

    }

    @Override
    public void unAssignWorkItem(WorkItem workItem, User user) {

    }

    @Override
    public void syncWorkItems(List<WorkItem> workItems) {
        workItemRepository.syncWorkItems(workItems);
    }

    @Override
    public List<Team> getTeams() {
        return null;
    }

    @Override
    public Team getTeam(long id) {
        return null;
    }

    @Override
    public long addOrUpdateTeam(Team team) {
        return 0;
    }

    @Override
    public void deleteTeam(Team team) {

    }

    @Override
    public void addTeamMember(Team team, User user) {

    }

    @Override
    public void removeTeamMember(Team team, User user) {

    }
}
