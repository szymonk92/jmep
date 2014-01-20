/* * JMEP - Java Mathematical Expression Parser. * Copyright (C) 1999  Jo Desmet *  * This library is free software; you can redistribute it and/or * modify it under the terms of the GNU Lesser General Public * License as published by the Free Software Foundation; either * version 2.1 of the License, or any later version. *  * This library is distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU * Lesser General Public License for more details. *  * You should have received a copy of the GNU Lesser General Public * License along with this library; if not, write to the Free Software * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA *  * You can contact the Original submitter of this library by * email at: Jo_Desmet@yahoo.com. *  */package com.googlecode.jmep.hooks;/** * Interface that defines a skeleton for a Unit call-back adaptor. * It should be implemented in order to use it with the addUnit method * of Environment. A way to accomplish this is by using in-line adaptor * classes:<p> *<ul><pre><code>Environment oEnv = new Environment(); *oEnv.addUnit("mm",new Unit() { *  apply(Object oValue) { *    if (oValue instanceof Double) *      return new Double(0.001*((Double)oValue).doubleValue()); *    if (oValue instanceof Integer) *      return new Double(0.001*((Integer)oValue).intValue()); *    return null; *  } *}); * </code></pre></ul> *  * @see com.iabcinc.jmep.Environment */public interface Unit {  /**   * Defines the unit's behaviour. This method is expected to   * check the arguments validity, and in case of any problems (unsupported   * argument type) it should return <code>null</code>.   * @param oValue the argument on which the the unit will perform an action   * @return the result after applying the unit, <code>null</code> in case of problems.   * Supported return types are: <code>String</code>, <code>Integer</code> and <code>Double</code>.   */  public Object apply(Object oValue);}