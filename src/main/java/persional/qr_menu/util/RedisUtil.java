package persional.qr_menu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Utility Class for QR Menu API
 * Provides convenient methods for Redis operations
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * Specify cache expiration time
     * @param key key
     * @param time time (seconds)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get expiration time based on key
     * @param key key, cannot be null
     * @return time (seconds) returns 0 means permanent validity
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Check if key exists
     * @param key key
     * @return true if exists, false if not
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete cache
     * @param key can pass one value or multiple
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(List.of(key));
            }
        }
    }

    // ============================String=============================

    /**
     * Get regular cache
     * @param key key
     * @return value
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * Set regular cache
     * @param key key
     * @param value value
     * @return true success, false failure
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Set regular cache and time
     * @param key key
     * @param value value
     * @param time time (seconds) time must be greater than 0, if time is less than or equal to 0, it will be set indefinitely
     * @return true success, false failure
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Increment
     * @param key key
     * @param delta how much to increase (greater than 0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("Increment factor must be greater than 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Decrement
     * @param key key
     * @param delta how much to decrease (less than 0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("Decrement factor must be greater than 0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * HashGet
     * @param key key, cannot be null
     * @param item item, cannot be null
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * Get all key-value pairs corresponding to hashKey
     * @param key key
     * @return corresponding multiple key-value pairs
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key key
     * @param map corresponding multiple key-value pairs
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet and set time
     * @param key key
     * @param map corresponding multiple key-value pairs
     * @param time time (seconds)
     * @return true success, false failure
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put data into a hash table, if it doesn't exist, it will be created
     * @param key key
     * @param item item
     * @param value value
     * @return true success, false failure
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put data into a hash table, if it doesn't exist, it will be created
     * @param key key
     * @param item item
     * @param value value
     * @param time time (seconds) Note: if existing hash table has time, this will replace the original time
     * @return true success, false failure
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete value from hash table
     * @param key key, cannot be null
     * @param item item, can be multiple, cannot be null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * Check if there is a value for this item in the hash table
     * @param key key, cannot be null
     * @param item item, cannot be null
     * @return true exists, false does not exist
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * Hash increment, if it doesn't exist, it will create one and return the new value
     * @param key key
     * @param item item
     * @param by how much to increase (greater than 0)
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * Hash decrement
     * @param key key
     * @param item item
     * @param by how much to decrease (less than 0)
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * Get all values in Set based on key
     * @param key key
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query from a set based on value to see if it exists
     * @param key key
     * @param value value
     * @return true exists, false does not exist
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put data into set cache
     * @param key key
     * @param values values, can be multiple
     * @return number of successful entries
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Put set data into cache
     * @param key key
     * @param time time (seconds)
     * @param values values, can be multiple
     * @return number of successful entries
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get the length of set cache
     * @param key key
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Remove values from set
     * @param key key
     * @param values values, can be multiple
     * @return number of removed entries
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * Get list cache content
     * @param key key
     * @param start start
     * @param end end (0 to -1 represents all values)
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get list cache length
     * @param key key
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get value in list by index
     * @param key key
     * @param index index (>= 0)
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Put list in cache
     * @param key key
     * @param value value
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put list in cache
     * @param key key
     * @param value value
     * @param time time (seconds)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put list in cache
     * @param key key
     * @param value value
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Put list in cache
     * @param key key
     * @param value value
     * @param time time (seconds)
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modify a certain data in list based on index
     * @param key key
     * @param index index
     * @param value value
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove N values that are value
     * @param key key
     * @param count how many to remove
     * @param value value
     * @return number of removed entries
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}