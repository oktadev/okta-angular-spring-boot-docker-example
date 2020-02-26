package com.okta.developer.notes

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(val repository: NotesRepository) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        for (x in 0..1000) {
            repository.save(Note(title = "Note ${x}", user = "matt.raible@okta.com"))
        }
        repository.findAll().forEach { println(it) }
    }
}
