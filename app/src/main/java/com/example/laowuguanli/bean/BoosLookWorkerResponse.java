package com.example.laowuguanli.bean;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class BoosLookWorkerResponse {
    private WorkerInformationBean[] records;//数据
    private int total;//一共多少数据
    private int size;//一页多少数据
    private int current;//第几页
    private int pages;//一共多少页

    @NonNull
    @Override
    public String toString() {
        return "BoosLookWorkerResponse{" +
                "records=" + Arrays.toString(records) +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", pages=" + pages +
                '}';
    }

    public WorkerInformationBean[] getRecords() {
        return records;
    }

    public BoosLookWorkerResponse setRecords(WorkerInformationBean[] records) {
        this.records = records;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public BoosLookWorkerResponse setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getSize() {
        return size;
    }

    public BoosLookWorkerResponse setSize(int size) {
        this.size = size;
        return this;
    }

    public int getCurrent() {
        return current;
    }

    public BoosLookWorkerResponse setCurrent(int current) {
        this.current = current;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public BoosLookWorkerResponse setPages(int pages) {
        this.pages = pages;
        return this;
    }
}
