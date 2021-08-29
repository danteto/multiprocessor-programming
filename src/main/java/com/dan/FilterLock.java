package com.dan;

public class FilterLock implements Lock {
    private int[] level;
    private int[] victim;

    public FilterLock(final int size) {
        this.level = new int[size];
        this.victim = new int[size];
        for (int i = 0; i < size; i++) {
            level[i] = 0;
        }
    }

    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId();
        for (int i = 0; i < level.length; ++i) {
            level[me] = i;
            victim[i] = me;

            // spin while conflicts exist
            for (int k = 0; k < level.length; ++k) {
                if (k != me) {
                    while (level[k] >= i && victim[i] == me) {
                    }
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId();
        level[me] = 0;
    }
}
