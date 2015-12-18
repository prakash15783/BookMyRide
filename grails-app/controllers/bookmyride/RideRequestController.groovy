package bookmyride

import org.springframework.dao.DataIntegrityViolationException

class RideRequestController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [rideRequestInstanceList: RideRequest.list(params), rideRequestInstanceTotal: RideRequest.count()]
    }

    def create() {
        [rideRequestInstance: new RideRequest(params)]
    }

    def save() {
        def rideRequestInstance = new RideRequest(params)
        if (!rideRequestInstance.save(flush: true)) {
            render(view: "create", model: [rideRequestInstance: rideRequestInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), rideRequestInstance.id])
        redirect(action: "show", id: rideRequestInstance.id)
    }

    def show(Long id) {
        def rideRequestInstance = RideRequest.get(id)
        if (!rideRequestInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "list")
            return
        }

        [rideRequestInstance: rideRequestInstance]
    }

    def edit(Long id) {
        def rideRequestInstance = RideRequest.get(id)
        if (!rideRequestInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "list")
            return
        }

        [rideRequestInstance: rideRequestInstance]
    }

    def update(Long id, Long version) {
        def rideRequestInstance = RideRequest.get(id)
        if (!rideRequestInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (rideRequestInstance.version > version) {
                rideRequestInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'rideRequest.label', default: 'RideRequest')] as Object[],
                          "Another user has updated this RideRequest while you were editing")
                render(view: "edit", model: [rideRequestInstance: rideRequestInstance])
                return
            }
        }

        rideRequestInstance.properties = params

        if (!rideRequestInstance.save(flush: true)) {
            render(view: "edit", model: [rideRequestInstance: rideRequestInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), rideRequestInstance.id])
        redirect(action: "show", id: rideRequestInstance.id)
    }

    def delete(Long id) {
        def rideRequestInstance = RideRequest.get(id)
        if (!rideRequestInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "list")
            return
        }

        try {
            rideRequestInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'rideRequest.label', default: 'RideRequest'), id])
            redirect(action: "show", id: id)
        }
    }
}
