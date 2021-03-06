package se.taskr.model;

/**
 * Created by kawi01 on 2017-05-17.
 */

public abstract class BaseEntity {
    public static final long DEFAULT_ID = 0;
    protected static final String DEFAULT_ITEMKEY = null;

    protected final long id;
    protected final String itemKey;

    protected BaseEntity(long id, String itemKey) {
        this.id = id;
        this.itemKey = itemKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    public long getId() {
        return id;
    }

    public boolean hasBeenPersisted() {
        return id != DEFAULT_ID;
    }

    public boolean hasBeenSavedToServer() {
        return itemKey != DEFAULT_ITEMKEY;
    }

    @Override
    public boolean equals(Object obj) {
        BaseEntity entity = (BaseEntity) obj;
        return entity.getId() == id && entity.getItemKey().equals(itemKey);
    }


}
