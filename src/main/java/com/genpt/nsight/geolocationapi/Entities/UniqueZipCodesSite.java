package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Objects;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniqueZipCodesSite {

    public String shortZipCode;
    public BigDecimal latitude;
    public BigDecimal longitude;

    @Override
    public int hashCode() {
        return Objects.hash(shortZipCode, latitude, longitude);
    }

    // Checks if object attributes are equal to an existing object's attributes
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniqueZipCodesSite other = (UniqueZipCodesSite) obj;
        return Objects.equals(shortZipCode, other.shortZipCode) && Objects.equals(latitude, other.latitude)&& Objects.equals(longitude, other.longitude);
    }
}
