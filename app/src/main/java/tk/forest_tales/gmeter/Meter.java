package tk.forest_tales.gmeter;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity(
        active = true,
        nameInDb = "meters"
)
public class Meter {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String number;

    @NotNull
    private String name;


    @ToMany(referencedJoinProperty = "meterId")
    private List<MeterValue> meterValues;


    @Transient
    private Double diff;
    public Double getDiff() {
        return Math.round((tempValues[0].getValue() - tempValues[1].getValue()) * 100) / 100.0;
    }

    @Transient
    private MeterValue[] tempValues = new MeterValue[2];
    public MeterValue getLastValue() {
        return this.tempValues[0];
    }
    public MeterValue getPrevValue() {
        return this.tempValues[1];
    }

    private static void setTempValues(List<Meter> meters, Query<MeterValue> valueQuery, int index){
        for(Meter m: meters){
            valueQuery.setParameter(0, m.getId());
            List<MeterValue> mvs = valueQuery.list();
            if(mvs.size() > 0){
                m.tempValues[index] = mvs.get(0);
            }else{
                MeterValue temp = new MeterValue();
                temp.setDate("N/A");
                temp.setValue(new Double(0));
                m.tempValues[index] = temp;
            }
        }
    }

    public static void setLastValues(List<Meter> meters, Query<MeterValue> valueQuery){
        setTempValues(meters, valueQuery, 0);
    }

    public static void setPrevValues(List<Meter> meters, Query<MeterValue> valueQuery){
        setTempValues(meters, valueQuery, 1);
    }






    /** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1759983993)
private transient MeterDao myDao;

@Generated(hash = 1514693048)
public Meter(Long id, @NotNull String number, @NotNull String name) {
    this.id = id;
    this.number = number;
    this.name = name;
}

@Generated(hash = 936842546)
public Meter() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNumber() {
    return this.number;
}

public void setNumber(String number) {
    this.number = number;
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

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 110513999)
public List<MeterValue> getMeterValues() {
    if (meterValues == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MeterValueDao targetDao = daoSession.getMeterValueDao();
        List<MeterValue> meterValuesNew = targetDao._queryMeter_MeterValues(id);
        synchronized (this) {
            if (meterValues == null) {
                meterValues = meterValuesNew;
            }
        }
    }
    return meterValues;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 332227949)
public synchronized void resetMeterValues() {
    meterValues = null;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 88215849)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getMeterDao() : null;
}

}
