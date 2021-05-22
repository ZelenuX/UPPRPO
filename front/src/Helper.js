class Helper {
    static fetchHelper = function () {
        return fetch(...arguments)
            .then(response => response.ok?
            response.json():
            Promise.reject(response.status));
    }

    static fetchText = function () {
        return fetch(...arguments)
            .then(response => response.ok?
                response.text():
                Promise.reject(response.status));
    }
}

export default Helper;