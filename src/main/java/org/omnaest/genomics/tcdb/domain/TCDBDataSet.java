package org.omnaest.genomics.tcdb.domain;

import java.util.stream.Stream;

public interface TCDBDataSet
{
    public Stream<TCDBRecord> getRecords();
}
