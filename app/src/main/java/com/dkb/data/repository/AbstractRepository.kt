package com.dkb.data.repository

import com.dkb.data.server.ServerApi
import com.dkb.domain.repository.ICommonRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
abstract class AbstractRepository: ICommonRepository, KoinComponent {
    protected val server: ServerApi by inject()
}