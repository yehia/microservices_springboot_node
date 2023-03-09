import UserRepository from "../repository/UserRepository.js";
import UserException from "../exception/UserException.js";
import * as HttpStatus from "../../../config/constants/HttpStatus.js"

class UserService {

    async findByEmail(req) {
        try {
            const {email} = req.params;
            this.validateRequestData(email);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

            return {
                status: HttpStatus.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                }
            }
        } catch (err) {
            return {
                status: err.status ? err.status : HttpStatus.INTERNAL_SERVER_ERROR,
                message: err.message,
            }
        }
    }

    validateRequestData(email) {
        if (!email) {
            throw new UserException(HttpStatus.BAD_REQUEST, "User email was not informed.");
        }
    }

    validateUserNotFound(user) {
        if (!user) {
            throw new UserException(HttpStatus.NOT_FOUND, "User was not found.");
        }
    }
}

export default new UserService();