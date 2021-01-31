package org.omnaest.genomics.tcdb.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.omnaest.genomics.tcdb.domain.TCDBRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCDBDRepository
{
    @JsonProperty
    private List<TCDBRecord> records = new ArrayList<>();

    public void addRecords(Collection<TCDBRecord> records)
    {
        this.records.addAll(records);
    }

    public List<TCDBRecord> getRecords()
    {
        return this.records;
    }

}
