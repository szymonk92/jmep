/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.jmep;

/**
 * @param <T> Source Type
 * @param <U> Target Type
 * @author jd3714
 */
public interface UpgradeConversion<T, U> {
    U apply(T t);
}
