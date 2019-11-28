package br.com.customers.service.dto;

import br.com.customers.domain.enumeration.Risk;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.*;

public class ClientDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double monthlyIncome;

    @NotNull
    private Risk risk;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private List<AddressDTO> addresses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
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

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return id.equals(clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", monthlyIncome=" + monthlyIncome +
            ", risk=" + risk +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
