/**
 * Korisnik.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.czsmdp.model;

public class Korisnik  implements java.io.Serializable {
    private java.lang.String lokacija;

    private java.lang.String passwordHash;

    private java.lang.String username;

    public Korisnik() {
    }

    public Korisnik(
           java.lang.String lokacija,
           java.lang.String passwordHash,
           java.lang.String username) {
           this.lokacija = lokacija;
           this.passwordHash = passwordHash;
           this.username = username;
    }


    /**
     * Gets the lokacija value for this Korisnik.
     * 
     * @return lokacija
     */
    public java.lang.String getLokacija() {
        return lokacija;
    }


    /**
     * Sets the lokacija value for this Korisnik.
     * 
     * @param lokacija
     */
    public void setLokacija(java.lang.String lokacija) {
        this.lokacija = lokacija;
    }


    /**
     * Gets the passwordHash value for this Korisnik.
     * 
     * @return passwordHash
     */
    public java.lang.String getPasswordHash() {
        return passwordHash;
    }


    /**
     * Sets the passwordHash value for this Korisnik.
     * 
     * @param passwordHash
     */
    public void setPasswordHash(java.lang.String passwordHash) {
        this.passwordHash = passwordHash;
    }


    /**
     * Gets the username value for this Korisnik.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this Korisnik.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Korisnik)) return false;
        Korisnik other = (Korisnik) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lokacija==null && other.getLokacija()==null) || 
             (this.lokacija!=null &&
              this.lokacija.equals(other.getLokacija()))) &&
            ((this.passwordHash==null && other.getPasswordHash()==null) || 
             (this.passwordHash!=null &&
              this.passwordHash.equals(other.getPasswordHash()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLokacija() != null) {
            _hashCode += getLokacija().hashCode();
        }
        if (getPasswordHash() != null) {
            _hashCode += getPasswordHash().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Korisnik.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.czsmdp.mdp", "Korisnik"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lokacija");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.czsmdp.mdp", "lokacija"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passwordHash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.czsmdp.mdp", "passwordHash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.czsmdp.mdp", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
