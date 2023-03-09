import jwt from "jsonwebtoken";
import { promisify } from "util";

import AuthException from "./AuthException.js";

import * as Secrets from "../constants/Secrets.js";
import * as HttpStatus from "../constants/HttpStatus.js";

const emptySpace = " ";

export default async (req, res, next) => {
    try {
        const { authorization } = req.headers;
        if (!authorization) {
            throw new AuthException(HttpStatus.UNAUTHORIZED, "Access token was not informed.");
        }

        let accessToken = authorization;
        if (accessToken.includes(emptySpace)) {
            accessToken = accessToken.split(emptySpace)[1];
        } else {
            accessToken = authorization;
        }
        const decoded = await promisify(jwt.verify)(accessToken, Secrets.API_SECRET);
        req.authUser = decoded.authUser;
        return next();
    } catch (err) {
        const status = err.status ? err.status : HttpStatus.FORBIDDEN;
        return res.status(status).json({
            status, 
            message: err.message,
        });
    }
};