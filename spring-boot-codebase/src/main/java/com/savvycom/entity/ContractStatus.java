package com.savvycom.entity;

/**
 * @author lam.le
 * @created 27/09/2021
 */
public enum ContractStatus {
    CONTRACT_NEW(0),
    CONTRACT_CANCEL(1),
    CONTRACT_DONE(2);

    private final int value;

    ContractStatus(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
