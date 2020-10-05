package com.koshake1.lesson1;

import com.koshake1.lesson1.dao.CityHistoryDao;
import com.koshake1.lesson1.history.History;

import java.util.List;


public class HistorySource {

    private final CityHistoryDao historyDao;
    private List<History> history;
    public HistorySource(CityHistoryDao historyDao){
        this.historyDao = historyDao;
    }

    public List<History> getHistory(){
        if (history == null){
            LoadHistory();
        }
        return history;
    }

    public void LoadHistory(){
        history = historyDao.getAllHistory();
    }

    public long getCountHistory(){
        return historyDao.getHistoryCount();
    }

    public void addHistory(History history){
        long id = historyDao.insertHistory(history);
        LoadHistory();
    }

    public void updateHistory(History history){
        historyDao.updateHistory(history);
        LoadHistory();
    }

    public void removeHistory(long id){
        historyDao.deleteHistoryById(id);
        LoadHistory();
    }

    public void clearAll() {
        historyDao.deleteAll();
        LoadHistory();
    }

}
