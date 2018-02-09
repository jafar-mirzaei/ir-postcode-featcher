package model.dto.entity.database;


import model.dto.AbstractModel;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class RepositoryImpAbstract<PK extends Serializable, T> implements Repository<PK, T> {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryImpAbstract.class);
    private final RepositoryManager RM = RepositoryManager.INSTANCE;
    private final Class<T> classType;
    private final String entity;

    @SuppressWarnings("unchecked")
    protected RepositoryImpAbstract() {
        this.classType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.entity = this.classType.getSimpleName();
    }

    @Override
    public Optional<T> getByKey(PK key) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            T t = em.find(this.classType, key);
            return t != null ? Optional.of(t) : Optional.empty();
        } catch (Exception e) {
            logger.error(String.format("Exception@getByKey(%s). Message: %s", key, e.getMessage()));
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T persist(T entity) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            em.getTransaction().begin();
            if (entity instanceof AbstractModel) {
                AbstractModel am = (AbstractModel) entity;
                final int time = (int) (System.currentTimeMillis() / 1000);;
                am.setCreatedTime(time);
                am.setUpdatedTime(time);
                entity = (T) am;
            }
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
            return entity;

        } catch (Exception e) {
            em.getTransaction().rollback();
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            logger.error(String.format("Exception@persist(?). Message: %s", e.getMessage()));
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public int persistBatch(List<T> entityList) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            em.getTransaction().begin();
            int cnt = 0, size = entityList.size();
            for (T obj : entityList) {
                if (obj instanceof AbstractModel) {
                    AbstractModel am = (AbstractModel) obj;
                    final int time = (int) (System.currentTimeMillis() / 1000);
                    am.setCreatedTime(time);
                    am.setUpdatedTime(time);
                    obj = (T) am;
                }
                em.persist(obj);
                if (++cnt % 100 == 0 || cnt == size) {
                    em.flush();
                    em.clear();
                }
            }
            em.getTransaction().commit();
            return cnt;
        } catch (Exception e) {
            em.getTransaction().rollback();

            int size = (entityList != null) ? entityList.size() : -1;
            logger.error(String.format("Exception@persistBatch(%d). Message: %s", size, e.getMessage()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T update(T entity) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            em.getTransaction().begin();
            if (entity instanceof AbstractModel) {
                AbstractModel am = (AbstractModel) entity;
                am.setUpdatedTime();
                entity = (T) am;
            }
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(String.format("Exception@update(?). Message: %s", e.getMessage()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public int updateBatch(List<T> entityList) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            em.getTransaction().begin();
            int cnt = 0, size = entityList.size();
            for (T entity : entityList) {
                if (entity instanceof AbstractModel) {
                    AbstractModel am = (AbstractModel) entity;
                    am.setUpdatedTime();
                    entity = (T) am;
                }
                em.merge(entity);
                if (++cnt % 100 == 0 || cnt == size) {
                    em.flush();
                    em.clear();
                }
            }
            em.getTransaction().commit();
            return cnt;
        } catch (Exception e) {
            em.getTransaction().rollback();
            int size = (entityList != null) ? entityList.size() : -1;
            logger.error(String.format("Exception@updateBatch(%d). Message: %s", size, e.getMessage()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean block(long id, boolean block) throws Exception {
        if (id <= 0) {
            return false;
        }
        final Map<String, Object> map = new HashMap<>();
        final String query = String.format("UPDATE %s t SET t.updatedTime=:updatedTime, t.block=:block WHERE t.id=:id", this.entity);
        map.put("updatedTime", (int) (System.currentTimeMillis() / 1000));
        map.put("block", block);
        map.put("id", id);
        return 1 == update(query, map);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int update(String q, Map<String, Object> map) throws Exception {
        //// TODO: 4/24/17 set last updated time
        EntityManager em = RM.getEntityManager();
        try {
            Query query = em.createQuery(q);
            if (map != null) {
                for (Map.Entry<String, Object> kv : map.entrySet()) {
                    query.setParameter(kv.getKey(), kv.getValue());
                }
            }
            em.getTransaction().begin();
            int rowAffected = query.executeUpdate();
            em.getTransaction().commit();
            return rowAffected;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(String.format("Exception@update(%s, %d). Message: %s", q, (map != null) ? map.size() : 0, e.getMessage()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }


    @SuppressWarnings("unchecked")
    public void delete(T entity) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.remove(entity);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(String.format("Exception@deletePermanently(?). Message: %s", e.getCause()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public FetchResult<T> search(String q, Map<String, Object> map, int max, int offset) throws Exception {
        return (FetchResult<T>) searchAdvanced(q, "t", map, " ORDER BY t.id desc ", max, offset);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FetchResult<T> search(String q, Map<String, Object> map, String orderBy, int max, int offset) throws Exception {
        return (FetchResult<T>) searchAdvanced(q, "t", map, orderBy, max, offset);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FetchResult<Object> searchAdvanced(String q, String fields, Map<String, Object> map, String orderBy, int max, int offset) throws Exception {
        max = Math.min(max, 50);
        offset = offset >= 0 ? offset : 0;

        EntityManager em = RM.getEntityManager();
        try {
            FetchResult<Object> result = new FetchResult(max, offset);
            Query query = em.createQuery(String.format(q, fields) + " " + orderBy);
            if (map != null) {
                for (Map.Entry<String, Object> kv : map.entrySet()) {
                    query.setParameter(kv.getKey(), kv.getValue());
                }
            }
            query.setFirstResult((offset) * max);
            query.setMaxResults(max);
            result.setResult(query.getResultList());

            query = em.createQuery(String.format(q, "count(id)"));
            if (map != null) {
                for (Map.Entry<String, Object> kv : map.entrySet()) {
                    query.setParameter(kv.getKey(), kv.getValue());
                }
            }
            String total = "" + ((!q.toLowerCase().contains("group by")) ? query.getSingleResult() : query.getResultList().size());
            result.setTotal(total.isEmpty() ? 0 : Integer.valueOf(total));

            return result;
        } catch (Exception e) {
            logger.error(String.format("Exception@search(%s, %d). Message: %s", q, (map != null) ? map.size() : 0, e.getMessage()));
            throw new Exception(e.getMessage());

        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List findAllBy(String q, Map<String, Object> map) throws Exception {
        return findAllBy(q, map, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List findAllBy(String q, Map<String, Object> map, int max) throws Exception {

        EntityManager em = RM.getEntityManager();
        try {
            if (!q.toLowerCase().contains("group by") && !q.toLowerCase().contains("order by")) {
                q += " ORDER BY t.id desc";
            }
            Query query = em.createQuery(q);
            if (max > 0) {
                query.setMaxResults(max);
            }
            if (map != null) {
                for (Map.Entry<String, Object> kv : map.entrySet()) {
                    query.setParameter(kv.getKey(), kv.getValue());
                }
            }
            return query.getResultList();
        } catch (Exception e) {
            logger.error(String.format("Exception@findAllBy(%s, %d). Message: %s", q, (map != null) ? map.size() : 0, e.getMessage()));
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findBy(String q, Map<String, Object> map) throws Exception {
        try {
            List<T> res = findAllBy(q, map);
            if (res.size() > 0) {
                return res.get(0);
            }
        } catch (Exception e) {
            logger.error(String.format("Exception@findBy(%s, %d). Message: %s", q, (map != null) ? map.size() : 0, e.getMessage()));
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public int executeNative(String q) throws Exception {
        EntityManager em = RM.getEntityManager();
        try {
            Query query = em.createNativeQuery(q);
            em.getTransaction().begin();
            int rowAffected = query.executeUpdate();
            em.getTransaction().commit();
            return rowAffected;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(String.format("Exception@executeNative(%s). Message: %s", q, e.getMessage()));
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                String name = (cause.getConstraintName() != null) ? cause.getConstraintName() : cause.getSQLException().getMessage();
                throw new Exception(name);
            }
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
    }
}