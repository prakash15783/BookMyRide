package bookmyride



import org.junit.*
import grails.test.mixin.*

@TestFor(BMRController)
@Mock(BMR)
class BMRControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/request/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.requestInstanceList.size() == 0
        assert model.requestInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.requestInstance != null
    }

    void testSave() {
        controller.save()

        assert model.requestInstance != null
        assert view == '/request/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/request/show/1'
        assert controller.flash.message != null
        assert BMR.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/request/list'

        populateValidParams(params)
        def request = new BMR(params)

        assert request.save() != null

        params.id = request.id

        def model = controller.show()

        assert model.requestInstance == request
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/request/list'

        populateValidParams(params)
        def request = new BMR(params)

        assert request.save() != null

        params.id = request.id

        def model = controller.edit()

        assert model.requestInstance == request
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/request/list'

        response.reset()

        populateValidParams(params)
        def request = new BMR(params)

        assert request.save() != null

        // test invalid parameters in update
        params.id = request.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/request/edit"
        assert model.requestInstance != null

        request.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/request/show/$request.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        request.clearErrors()

        populateValidParams(params)
        params.id = request.id
        params.version = -1
        controller.update()

        assert view == "/request/edit"
        assert model.requestInstance != null
        assert model.requestInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/request/list'

        response.reset()

        populateValidParams(params)
        def request = new BMR(params)

        assert request.save() != null
        assert BMR.count() == 1

        params.id = request.id

        controller.delete()

        assert BMR.count() == 0
        assert BMR.get(request.id) == null
        assert response.redirectedUrl == '/request/list'
    }
}
