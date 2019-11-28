package br.com.customers.service.dto;


import br.com.customers.domain.enumeration.TypeAddress;

import java.time.ZonedDateTime;
import java.util.Objects;

public class AddressDTO {

    private Long id;

    private TypeAddress typeAddress;

    private String streetAddress;

    private String postalCode;

    private Integer number;

    private String complement;

    private String district;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Long userId;

    private String userLogin;

    private Long clientId;

    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAddress getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(TypeAddress typeAddress) {
        this.typeAddress = typeAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + id +
            ", typeAddress=" + typeAddress +
            ", streetAddress='" + streetAddress + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", number=" + number +
            ", complement='" + complement + '\'' +
            ", district='" + district + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", userId=" + userId +
            ", userLogin='" + userLogin + '\'' +
            '}';
    }
}
