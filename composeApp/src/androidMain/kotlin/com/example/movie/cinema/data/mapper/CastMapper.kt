package com.example.movie.cinema.data.mapper

import com.example.movie.cinema.data.dto.PersonDto
import com.example.movie.cinema.model.CastMember

fun PersonDto.toDomain(): CastMember {
    return CastMember(
        fullName = fullName,
        roleName = departmentName ?: professionText,
        imagePath = profileImagePath,
        castOrder = null
    )
}

fun List<PersonDto>.toCastDomainList(): List<CastMember> {
    return map { personDto ->
        personDto.toDomain()
    }
}