package fr.lewon.rosa.store

/**
 * Mocked store, should be replaced with a store using a database
 */
abstract class AbstractMockedStore<T> {

    protected val items = ArrayList<T>()

    fun add(item: T): Boolean {
        return items.add(item)
    }

}