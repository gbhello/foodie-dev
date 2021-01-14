package org.n3r.idworker;

/**
 * @author gengbin
 * @date 2021/1/14
 */
public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}
