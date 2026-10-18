package ru.hse.vyshkat.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.ElectricScooter;

class SimplicityBeginnerPolicyTest {

    private final SimplicityBeginnerPolicy policy = new SimplicityBeginnerPolicy();

    @Test
    void simplicity6IsOkAnd5IsNot() {
        assertTrue(policy.isForBeginner(new Bicycle(1, "Stels", 6, 0)));
        assertFalse(policy.isForBeginner(new Bicycle(2, "Stels", 5, 0)));
    }

    @Test
    void worksForElectricToo() {
        assertTrue(policy.isForBeginner(new ElectricScooter(1, "Ninebot", 10, 0, 0.5)));
        assertFalse(policy.isForBeginner(new ElectricScooter(2, "Kugoo", 1, 0, 0.5)));
    }
}
