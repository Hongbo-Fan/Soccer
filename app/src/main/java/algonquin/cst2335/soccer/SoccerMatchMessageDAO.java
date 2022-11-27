package algonquin.cst2335.soccer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//objects that performs CRUD operations
@Dao
public interface SoccerMatchMessageDAO {

    @Insert
    public void insertMatch(SoccerMatchMessage m);

    //This matches the @Entity Class name
    @Query("Select * from SoccerMatchMessage;")
    public List<SoccerMatchMessage> getAllMatches();

//    @Query("Select * from ChatMessage where id = :_id;")
//    public ChatRoom.ChatMessage getMessageById(int _id);

    @Delete
    public void deleteMatches(SoccerMatchMessage m);
}
