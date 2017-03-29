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

    @ToOne
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

@Generated(hash = 639784962)
private transient boolean meter__refreshed;

/** To-one relationship, resolved on first access. */
@Generated(hash = 117684536)
public Meter getMeter() {
    if (meter != null || !meter__refreshed) {
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MeterDao targetDao = daoSession.getMeterDao();
        targetDao.refresh(meter);
        meter__refreshed = true;
    }
    return meter;
}

/** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
@Generated(hash = 1195253527)
public Meter peakMeter() {
    return meter;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1301700974)
public void setMeter(Meter meter) {
    synchronized (this) {
        this.meter = meter;
        meter__refreshed = true;
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
