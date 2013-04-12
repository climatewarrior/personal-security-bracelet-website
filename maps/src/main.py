#!/usr/bin/python
# -*- coding: utf-8 -*-

# Copyright (C) 2012 Gabriel J. Pérez Irizarry and Andrea Del Risco
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License, version 3,
# as published by the Free Software Foundation.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program. If not, see <http://www.gnu.org/licenses/>.

from flask import Flask, render_template, request, flash, redirect, url_for, jsonify

from flask.ext.bootstrap import Bootstrap

import json_app
import time

app = json_app.make_json_app('__main__')
Bootstrap(app)

@app.route('/')
def index():
    
    return render_template('maps.html')

@app.route('/get_data')
def ajax():
    timestamp = time.time()

    locations = [[-25.363882,131.044922], [33.762737,-84.401464]]
    return jsonify({'timestamp':timestamp, 'locations':locations})

if __name__ == '__main__':
    app.run(debug=True)

