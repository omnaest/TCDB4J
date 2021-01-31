package org.omnaest.genomics.tcdb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.genomics.tcdb.component.TCDBDRepository;
import org.omnaest.genomics.tcdb.domain.TCDBDataSet;
import org.omnaest.genomics.tcdb.domain.TCDBRecord;
import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.csv.CSVUtils;
import org.omnaest.utils.element.cached.CachedElement;

public class TCDBUtils
{
    public static interface Loader
    {
        public TCDBDataSet from(File file) throws FileNotFoundException;

        public TCDBDataSet from(Reader reader);

        public TCDBDataSet from(InputStream inputStream, Charset encoding);

        public TCDBDataSet from(InputStream inputStream);

        /**
         * Uses a local cache file named 'cache/tcdbRepository.json'
         * 
         * @return
         */
        public Loader usingLocalCache();

        /**
         * Loads the data from the cache only
         * 
         * @return
         */
        public TCDBDataSet fromCache();
    }

    public static Loader load()
    {
        return new Loader()
        {
            private boolean usingLocalCache = false;

            @Override
            public TCDBDataSet from(File file) throws FileNotFoundException
            {
                return this.from(new FileInputStream(file));
            }

            @Override
            public TCDBDataSet from(InputStream inputStream)
            {
                return this.from(inputStream, StandardCharsets.UTF_8);
            }

            @Override
            public TCDBDataSet from(InputStream inputStream, Charset encoding)
            {
                return this.from(new InputStreamReader(new BufferedInputStream(inputStream), encoding));
            }

            @Override
            public Loader usingLocalCache()
            {
                this.usingLocalCache = true;
                return this;
            }

            @Override
            public TCDBDataSet fromCache()
            {
                return this.from((Reader) null);
            }

            @Override
            public TCDBDataSet from(Reader reader)
            {
                CachedElement<TCDBDRepository> repositoryCache = this.resolveRepositoryCache(reader);
                TCDBDRepository repository = repositoryCache.get();

                return new TCDBDataSet()
                {

                    @Override
                    public Stream<TCDBRecord> getRecords()
                    {
                        return repository.getRecords()
                                         .stream();
                    }
                };
            }

            private CachedElement<TCDBDRepository> resolveRepositoryCache(Reader reader)
            {
                CachedElement<TCDBDRepository> repositoryCache = CachedElement.of(() ->
                {
                    TCDBDRepository repository = new TCDBDRepository();

                    try
                    {
                        repository.addRecords(CSVUtils.parse()
                                                      .from(reader)
                                                      .get()
                                                      .map(JSONHelper.toObjectWithTypeMapper(TCDBRecord.class))
                                                      .collect(Collectors.toList()));
                    }
                    catch (IOException e)
                    {
                        throw new IllegalStateException(e);
                    }

                    return repository;
                });

                if (this.usingLocalCache)
                {
                    repositoryCache = repositoryCache.withFileCache(new File("cache/tcdbRepository.json"), JSONHelper.serializer(),
                                                                    JSONHelper.deserializer(TCDBDRepository.class));
                }
                return repositoryCache;
            }
        };
    }
}
