import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class PoolEditComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      pool: {
        id: this.props.match.params.id,
        name: '',
        questions: [],
      },
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.getPool();
  }

  getPool() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools/' + this.state.pool.id)
      .then(result => {
        this.setState({
          pool: result.data,
          isLoading: false,
        });
      })
      .catch(error => {
        this.setState({
          pool: [],
          isLoading: false,
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  validateSubmit(event) {
    // const form = event.target;
    const pool = this.state.pool;
    var outcome = true;
    // const properties = [
    //   'name'
    // ];

    try {
      if (pool.name.length === 0 || pool.name.length > 256) {
        outcome = false;
        // form.elements['name'].classList += " invalid";
      }
    } catch (err) {
      this.props.enqueueSnackbar('Error al validar los datos!', {
        variant: 'error',
      });
    }

    event.preventDefault();
    event.stopPropagation();

    if (!outcome) {
      this.props.enqueueSnackbar(
        'Debes completar todos los campos correctamente!',
        { variant: 'warning' }
      );
    }

    return outcome;
  }

  handleSubmit(event) {
    if (!this.validateSubmit(event)) {
      event.preventDefault();
      return;
    }

    this.setState({ isLoading: true });

    axios
      .put(
        process.env.REACT_APP_API_URL + '/pools/' + this.state.pool.id,
        this.state.pool
      )
      .then(result => {
        this.setState({
          pool: result.data,
        });

        this.props.history.push('/pools');
        this.props.enqueueSnackbar('Pozo actualizado!', { variant: 'success' });
      })
      .catch(error => {
        this.props.enqueueSnackbar('Error al actualizar los datos!', {
          variant: 'error',
        });
      });

    event.preventDefault();
  }

  handleChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      pool: {
        ...this.state.pool,
        [name]: value,
      },
    });
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <Typography variant="h4" gutterBottom>
              Editar Pozo
            </Typography>
            <div class="panelcrear">
              <div class="formcrear">
                <form onSubmit={this.handleSubmit}>
                  <TextField
                    id="filled-full-width"
                    label="Nombre"
                    type="text"
                    name="name"
                    value={this.state.pool.name}
                    onChange={this.handleChange}
                    placeholder="Ingrese tematica del pozo"
                    fullWidth
                    margin="normal"
                    variant="filled"
                    InputLabelProps={{
                      shrink: true,
                    }}
                  />

                  <div className="btn-group">
                    <Button component={Link} to="/pools">
                      Cancelar
                    </Button>

                    <Button
                      variant="contained"
                      color="primary"
                      className="btn-primary"
                      type="submit"
                    >
                      Guardar
                    </Button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(PoolEditComponent);
