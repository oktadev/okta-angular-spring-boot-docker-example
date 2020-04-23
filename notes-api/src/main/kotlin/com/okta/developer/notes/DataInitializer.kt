package com.okta.developer.notes

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DataInitializer(val repository: NotesRepository) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        for (x in 0..1000) {
            repository.save(Note(title = "Note ${x}", username = "matt.raible@okta.com"))
        }
        repository.findAll().forEach { println(it) }
    }
}
