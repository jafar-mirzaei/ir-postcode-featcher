package ir.jm.iranpostcode.model.dto.entity.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pegah on 12/21/16.
 */
public class FetchResult<T> {
    private int max;
    private int offset;
    private int total;
    private List<T> result;
    private Object info;

    public FetchResult() {
    }

    public FetchResult(int max, int offset) {
        this.max = max;
        this.offset = offset;
    }

    public FetchResult(int max, int offset, int total) {
        this.max = max;
        this.offset = offset;
        this.total = total;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @JsonIgnore
    public int getResultSize() {
        return (result != null) ? result.size() : 0;
    }

    public T getResultAt(int index) {
        return (index < getResultSize()) ? result.get(index) : null;
    }

    public void addResult(T obj) {
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(obj);
    }

    public void addResult(T obj, int index) {
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(index, obj);
    }
}
