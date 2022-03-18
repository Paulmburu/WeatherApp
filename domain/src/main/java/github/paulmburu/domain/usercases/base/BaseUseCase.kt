package github.paulmburu.domain.usercases.base

interface BaseUseCase<in Params, out T> {
    suspend operator fun invoke(params: Params): T
}