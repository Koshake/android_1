package com.koshake1.lesson1.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.koshake1.lesson1.history.History;

import java.util.List;
@Dao
public interface CityHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(History history);

    @Update
    void updateHistory(History history);

    @Delete
    void deleteHistory(History history);

    @Query("DELETE FROM history")
    void deleteAll();

    @Query("DELETE FROM history WHERE id = :id")
    void deleteHistoryById(long id);

    @Query("SELECT * FROM history")
    List<History> getAllHistory();

    @Query("SELECT * FROM history WHERE id = :id")
    History getHistoryById(long id);

    @Query("SELECT COUNT() FROM history")
    long getHistoryCount();
}
