import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';
import TextField from '@material-ui/core/TextField';
import FormHelperText from '@material-ui/core/FormHelperText';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class PoolCreateComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,

      pool: {
        name: '',
      },

      errors: {
        name: '',
      },
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.setState({ isLoading: false });
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

  validateSubmit(event) {
    const pool = this.state.pool;
    var outcome = true;

    try {
      if (pool.name.length === 0 || pool.name.length > 256) {
        this.setState({
          errors: {
            name:
              'El campo es requerido y su largo debe ser menor a 256 caracteres.',
          },
        });
        outcome = false;
      }
    } catch (err) {
      this.props.enqueueSnackbar('Error al guardar pozo!', {
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
      .post(process.env.REACT_APP_API_URL + '/pools', this.state.pool)
      .then(result => {
        this.setState({
          isLoading: false,
          pool: result.data,
        });

        this.props.history.push('/pools');

        if (result.data.id != null) {
          console.log(result.data);
          this.props.enqueueSnackbar('Pozo creado!', { variant: 'success' });
        } else {
          this.props.enqueueSnackbar('Error al crear el pozo!', {
            variant: 'error',
          });
        }
      })
      .catch(error => {
        this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al crear el pozo!', {
          variant: 'error',
        });
      });

    event.preventDefault();
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <Typography variant="h4" gutterBottom>
              Crear Pozo
            </Typography>
            <paper>
              <div class="panelcrear">
                <div class="formcrear">
                  <form onSubmit={this.handleSubmit}>
                    <TextField
                      error={this.state.errors.name === ''}
                      id="filled-full-width"
                      label="Nombre"
                      type="text"
                      name="name"
                      onChange={this.handleChange}
                      placeholder="Ingrese tematica del pozo"
                      fullWidth
                      margin="normal"
                      variant="filled"
                      InputLabelProps={{
                        shrink: true,
                      }}
                    />
                    <FormHelperText>{this.state.errors.name}</FormHelperText>

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
                        Crear
                      </Button>
                    </div>
                  </form>
                </div>
              </div>
            </paper>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(PoolCreateComponent);
