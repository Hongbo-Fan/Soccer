package algonquin.cst2335.soccer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//store the data each soccer match(id,title, team1, team2, videoUrl, date, competition, thumbnailUrl)
@Entity
public class SoccerMatchMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name="Title")
    public String title;

    @ColumnInfo(name="Team1")
    public String team1;

    @ColumnInfo(name="Team2")
    public String team2;

    @ColumnInfo(name="VideoUrl")
    public String videoUrl;

    @ColumnInfo(name="Date")
    public String date;

    @ColumnInfo(name="Competition")
    public String competition;

    @ColumnInfo(name="ThumbnailUrl")
    public String thumbnailUrl;

    public SoccerMatchMessage(){

    }

    public SoccerMatchMessage(String title, String team1, String team2, String videoUrl, String date, String competition, String thumbnailUrl){
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.videoUrl = videoUrl;
        this.date = date;
        this.competition = competition;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTeam1() {
        return team1;
    }

    public int getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCompetition() {
        return competition;
    }

    public String getDate() {
        return date;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTeam2() {
        return team2;
    }
}
