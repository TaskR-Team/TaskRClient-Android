package se.taskr.teamdetail;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import se.taskr.R;
import se.taskr.model.Team;
import se.taskr.repository.TaskRContentProvider;
import se.taskr.repository.TaskRContentProviderImpl;

/**
 * Created by kawi01 on 2017-06-01.
 */

public class TeamDetailViewModel {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();
    public ObservableField<String> nameError = new ObservableField<>();
    public ObservableField<String> descriptionError = new ObservableField<>();
    public ObservableField<Boolean> newTeam = new ObservableField<>();

    private final TeamDetailActivity activity;
    private Context context;
    private TaskRContentProvider contentProvider;
    private Team team;

    public TeamDetailViewModel(Context context, Team team, boolean newTeam, TeamDetailActivity activity) {
        this.context = context;
        contentProvider = TaskRContentProviderImpl.getInstance(context);
        name.set(team.getName());
        description.set(team.getDescription());
        this.newTeam.set(newTeam);
        this.team = team;
        this.activity = activity;
    }

    public void save() {
        if (name.get().length() < 5) {
            nameError.set("Name error");
        }
        else if (description.get().length() < 5) {
            descriptionError.set("Description error");
        } else {
            team.setName(name.get());
            team.setDescription(description.get());
            Long id = contentProvider.addOrUpdateTeam(team);
            if (id != null) {
                Toast.makeText(context, R.string.team_updated, Toast.LENGTH_LONG).show();
            }
            if (newTeam.get()) {
                activity.finish();
            }
        }
    }
}
