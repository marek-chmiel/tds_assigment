package uk.co.testdrivensolutions.parkinglot.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;

@Converter(autoApply = true)
public class VehicleTypeConverter implements AttributeConverter<VehicleType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(VehicleType vehicleType) {
        return vehicleType != null ? vehicleType.getType() : null;
    }
    @Override
    public VehicleType convertToEntityAttribute(Integer type) {
        return VehicleType.fromType(type);
    }

}
