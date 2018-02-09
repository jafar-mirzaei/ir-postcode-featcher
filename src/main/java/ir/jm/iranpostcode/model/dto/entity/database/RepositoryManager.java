package ir.jm.iranpostcode.model.dto.entity.database;

import ir.jm.iranpostcode.model.dto.entity.AutocompleteRes;
import ir.jm.iranpostcode.model.dto.entity.AvensRes;
import ir.jm.iranpostcode.model.dto.entity.BuildsRes;
import ir.jm.iranpostcode.model.dto.entity.Data;
import ir.jm.iranpostcode.model.dto.entity.Datum;
import ir.jm.iranpostcode.model.dto.entity.PostCodeRes;
import ir.jm.iranpostcode.model.dto.entity.Query;
import ir.jm.iranpostcode.model.dto.entity.Suggestion;
import ir.jm.iranpostcode.model.dto.entity.impl.AutocompleteResRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.AvensResRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.BuildsResRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.DataRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.DatumRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.PostCodeResRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.QueryRepositoryImpl;
import ir.jm.iranpostcode.model.dto.entity.impl.SuggestionRepositoryImpl;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum RepositoryManager {
    INSTANCE;
    private final EntityManagerFactory emFactory;

    RepositoryManager() {
        //persistence-unit
        this.emFactory = Persistence.createEntityManagerFactory("tets-post");
    }

    public EntityManager getEntityManager() {
        return this.emFactory.createEntityManager();
    }

    public void close() {
        this.emFactory.close();
    }

    //----------------------------------------------------------------------------------------------

    private static final Map<Class, RepositoryImpAbstract> ENTITY_REPO_MAP = new HashMap<>();

    static {
        ENTITY_REPO_MAP.put(AutocompleteRes.class, new AutocompleteResRepositoryImpl());
        ENTITY_REPO_MAP.put(AvensRes.class, new AvensResRepositoryImpl());
        ENTITY_REPO_MAP.put(BuildsRes.class, new BuildsResRepositoryImpl());
        ENTITY_REPO_MAP.put(Data.class, new DataRepositoryImpl());
        ENTITY_REPO_MAP.put(Datum.class, new DatumRepositoryImpl());
        ENTITY_REPO_MAP.put(PostCodeRes.class, new PostCodeResRepositoryImpl());
        ENTITY_REPO_MAP.put(Query.class, new QueryRepositoryImpl());
        ENTITY_REPO_MAP.put(Suggestion.class, new SuggestionRepositoryImpl());
    }

    public static RepositoryImpAbstract getByEntity(Class clazz) {
        return ENTITY_REPO_MAP.get(clazz);
    }
}
