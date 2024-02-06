package in.codingstreams.etuserauthservice.controller.mapper;

import in.codingstreams.etuserauthservice.controller.model.AuthResponseDto;
import in.codingstreams.etuserauthservice.service.model.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthResponseMapper {

    AuthResponseMapper INSTANCE = Mappers.getMapper(AuthResponseMapper.class);

    AuthResponseDto toAuthResponseDto(AuthResponse authResponse);
}
