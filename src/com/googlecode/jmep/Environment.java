/* * JMEP - Java Mathematical Expression Parser. * Copyright (C) 1999  Jo Desmet *  * This library is free software; you can redistribute it and/or * modify it under the terms of the GNU Lesser General Public * License as published by the Free Software Foundation; either * version 2.1 of the License, or any later version. *  * This library is distributed in the hope that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU * Lesser General Public License for more details. *  * You should have received a copy of the GNU Lesser General Public * License along with this library; if not, write to the Free Software * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA *  * You can contact the Original submitter of this library by * email at: Jo_Desmet@yahoo.com. *  */ package com.googlecode.jmep;import com.googlecode.jmep.hooks.Constant;import com.googlecode.jmep.hooks.Unit;import com.googlecode.jmep.hooks.Variable;import com.googlecode.jmep.hooks.Function;import com.googlecode.jmep.hooks.BinaryOperator;import com.googlecode.jmep.hooks.UnaryOperator;import java.util.EnumMap;import java.util.Map;import java.util.HashMap;/** * The container for operators, functions, variables and units. The Environment * allows for operators to be customized to introduce new value types, or to take * into account how numbers should be constraint for a specific domain (Engineering vs Financial). * @author Jo Desmet */public class Environment {  private final Map<String,Variable> variables;  private final Map<String,Function> functions;  private final Map<String,Unit> units;  private final Map<BinaryOperatorType,Map<SimpleClassPair, BinaryOperator>> binaryOperators;  private final Map<UnaryOperatorType,Map<Class, UnaryOperator>> unaryOperators;  /**   * Allocates the Expression Environment.   */  public Environment() {    variables = new HashMap<>();    functions = new HashMap<>();    units = new HashMap<>();    binaryOperators = new EnumMap<>(BinaryOperatorType.class);    unaryOperators = new EnumMap<>(UnaryOperatorType.class);  }  /**   * Adds a labeled String constant to the environment.   * @param name the label attached to the constant.   * @param value the string value of the labeled constant.   */  public <T> void addConstant(String name,final T value) {    variables.put(name,new Constant(value));  }    /**   * Adds a labeled variable to the environment. This is done by   * using an adapter class. You can either use an inner class or   * an anonymous class for this purpose.   * @param name the label attached to the variable.   * @param variable the variable call-back instance.   * @see com.iabcinc.jmep.Variable   */  public void addVariable(String name,Variable variable) {    variables.put(name,variable);  }  /**   * Returns a map containing all the variables and constants. The   * contents will be of type: String, Double, Integer or Variable.   * Note that you can interact directly with this map.   * @see com.iabcinc.jmep.Environment#addConstant   * @see com.iabcinc.jmep.Environment#addVariable   */  Map<String,Variable> getVariables() {    return variables;  }    /**   * Returns a map containing all the units. The contents will be   * only of type Unit.   * Note that you can interact directly with this map.   * @see com.iabcinc.jmep.Environment#addUnit   */  Map<String,Unit> getUnits() {    return units;  }    /**   * Returns a map containing all the functions. The contents will be   * only of type Function.   * Note that you can interact directly with this map.   * @see com.iabcinc.jmep.Environment#addFunction   */  Map<String,Function> getFunctions() {    return functions;  }    /**   * Returns the labeled variable. Depending how the variable was   * added, this could be: String, Double, Integer or Variable.   * @see com.iabcinc.jmep.Environment#addConstant   * @see com.iabcinc.jmep.Environment#addVariable   */  Variable getVariable(String name) {    return variables.get(name);  }    /**   * Returns the labeled unit.   * @see com.iabcinc.jmep.Environment#addUnit   */  Unit getUnit(String name) {    return (Unit)units.get(name);  }    /**   * Returns the labeled function.   * @see com.iabcinc.jmep.Environment#addFunction   */  Function getFunction(String name) {    return (Function)functions.get(name);  }    /**   * Adds a Function to the environment. This is done by using an   * adapter class. You can either us an inner class or an anonymous   * class for this purpose.   * @param name the label attached to the added function.   * @param function the function call-back instance.   * @see com.iabcinc.jmep.Function   */  public void addFunction(String name,Function function) {    functions.put(name,function);  }    /**   * Adds a Unit to the environment. This is done by using an   * adapter class. You can either us an inner class or an anonymous   * class for this purpose.   * @param name the label attached to the added unit.   * @param unit the unit call-back instance.   * @see com.iabcinc.jmep.Unit   */  public void addUnit(String name,Unit unit) {    units.put(name,unit);  }  public <T> void register(UnaryOperatorType operatorType,Class<T> t,final UnaryOperator<T> operator) {    Map<Class,UnaryOperator> implementations = unaryOperators.get(operatorType);    if (implementations == null) {      implementations = new HashMap<>();      unaryOperators.put(operatorType, implementations);    }    implementations.put(t,operator);  }  public <T,U> void register(BinaryOperatorType operatorType,Class<T> t,Class<U> u,final BinaryOperator<T,U> operator) {    Map<SimpleClassPair,BinaryOperator> implementations = binaryOperators.get(operatorType);    if (implementations == null) {      implementations = new HashMap<>();      binaryOperators.put(operatorType, implementations);    }    implementations.put(SimpleClassPair.of(t,u),operator);    if (t != u && operatorType.isCommutative())  {      // If operator is Commutative, then automatically store the commutative version if not already exists.      SimpleClassPair<U,T> p = SimpleClassPair.of(u, t);      if (!implementations.containsKey(p)) {        implementations.put(p, new BinaryOperator<U,T>() {          @Override          public Object apply(U uu, T tt) {            return operator.apply(tt,uu);          }        } );      }    }  }      Map<UnaryOperatorType,Map<Class, UnaryOperator>> getUnaryOperators() {    return this.unaryOperators;  }    Map<BinaryOperatorType,Map<SimpleClassPair, BinaryOperator>> getBinaryOperators() {    return this.binaryOperators;  }  }