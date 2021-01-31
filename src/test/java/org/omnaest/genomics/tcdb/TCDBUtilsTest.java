package org.omnaest.genomics.tcdb;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.genomics.tcdb.TCDBUtils;
import org.omnaest.genomics.tcdb.domain.TCDBDataSet;

public class TCDBUtilsTest
{

    @Test
    @Ignore
    public void testLoad() throws Exception
    {
        TCDBDataSet dataSet = TCDBUtils.load()
                                       .usingLocalCache()
                                       .from(new File("C:\\Z\\databases\\TCDB\\human.csv"));

        dataSet.getRecords()
               .forEach(System.out::println);
    }

}
