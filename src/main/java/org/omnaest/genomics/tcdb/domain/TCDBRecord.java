package org.omnaest.genomics.tcdb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCDBRecord
{
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Aliases")
    private String aliases;

    @JsonProperty("Accession")
    private String accession;

    @JsonProperty("TCID")
    public String id;

    @JsonProperty("Sequence")
    public String sequence;

    public String getAliases()
    {
        return this.aliases;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public String getAccession()
    {
        return this.accession;
    }

    public String getId()
    {
        return this.id;
    }

    public String getSequence()
    {
        return this.sequence;
    }

    @Override
    public String toString()
    {
        return "TCDBRecord [name=" + this.name + ", symbol=" + this.symbol + ", accession=" + this.accession + ", id=" + this.id + ", sequence=" + this.sequence
                + "]";
    }

}
