/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

/**
 *
 * @author victor.lorenzana
 */
public class RestParameter {

    private final String name;
    private final String[] values;
    public RestParameter(String name,String[] values)
    {
        this.name=name;
        this.values=values;
    }
    public String getName()
    {
        return name;
    }
    public String[] getValues()
    {
        return values;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RestParameter other = (RestParameter) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return  name;
    }
    

}
