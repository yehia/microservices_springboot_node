import express from "express";

import UserRoute from "./src/modules/user/routes/UserRoute.js";

import * as db from "./src/config/db/InitialData.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

db.createInitialData();

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-API',
        status: 'UP',
        httpStatus: 200,
    });
});

app.use(express.json());
app.use(UserRoute);

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});