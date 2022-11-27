package algonquin.cst2335.soccer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//declare a database, the table is ChatMessage
@Database(entities = {SoccerMatchMessage.class}, version = 1)
public abstract class SoccerDatabase extends RoomDatabase {

    //return the DAO class
    public abstract SoccerMatchMessageDAO smDAO();
}
