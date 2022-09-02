package android.ptc.com.ptcflixing.core

interface EntityMapper<EntityModel, DomainModel> {

    fun EntityModel.mapToDomainModel(): DomainModel

    fun DomainModel.mapToEntity(): EntityModel
}