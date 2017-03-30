package tk.forest_tales.gmeter;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(
        active = true,
        nameInDb = "meter_values"
)
public class MeterValue {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String date;

    @NotNull
    private Double value;

    private long meterId;

    @ToOne(joinProperty = "meterId")
    private Meter meter;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1356685872)
private transient MeterValueDao myDao;

@Generated(hash = 1267531476)
public MeterValue(Long id, @NotNull String date, @NotNull Double value,
        long meterId) {
    this.id = id;
    this.date = date;
    this.value = value;
    this.meterId = meterId;
}

@Generated(hash = 1073914293)
public MeterValue() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getDate() {
    return this.date;
}

public void setDate(String date) {
    this.date = date;
}

public Double getValue() {
    return this.value;
}

public void setValue(Double value) {
    this.value = value;
}

public long getMeterId() {
    return this.meterId;
}

public void setMeterId(long meterId) {
    this.meterId = meterId;
}

@Generated(hash = 442189471)
private transient Long meter__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1757995799)
public Meter getMeter() {
    long __key = this.meterId;
    if (meter__resolvedKey == null || !meter__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MeterDao targetDao = daoSession.getMeterDao();
        Meter meterNew = targetDao.load(__key);
        synchronized (this) {
            meter = meterNew;
            meter__resolvedKey = __key;
        }
    }
    return meter;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 738368930)
public void setMeter(@NotNull Meter meter) {
    if (meter == null) {
        throw new DaoException(
                "To-one property 'meterId' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.meter = meter;
        meterId = meter.getId();
        meter__resolvedKey = meterId;
    }
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1489655882)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getMeterValueDao() : null;
}


}
